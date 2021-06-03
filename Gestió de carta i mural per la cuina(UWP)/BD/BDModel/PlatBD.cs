﻿using BD.Model;
using BD.Utilities;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Data.Common;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.Storage;
using Windows.UI.Xaml.Media.Imaging;

namespace BD.BDModel
{
    public class PlatBD
    {
        public static ObservableCollection<Plat> GetLlistaPlats()
        {
            try
            {

                using (ContextDB context = new ContextDB())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {

                            consulta.CommandText =
                        $@"   SELECT * FROM `plats` ORDER BY `CATEGORIA` ";

                            var reader = consulta.ExecuteReader();
                            ObservableCollection<Plat> plats = new ObservableCollection<Plat>();
                            while (reader.Read())
                            {
                                Plat p = new Plat();
                                BD_Utils.Llegeix(reader, out p.id, "PLAT_ID");
                                BD_Utils.Llegeix(reader, out p.nom, "NOM");
                                BD_Utils.Llegeix(reader, out p.descripcio, "DESCRIPCIO_MD", null);
                                BD_Utils.Llegeix(reader, out p.preu, "PREU");
                                BD_Utils.Llegeix(reader, out p.fotoBytes, "FOTO");
                                BD_Utils.Llegeix(reader, out p.disponible, "DISPONIBLE");

                                long categoriaId = 0;
                                BD_Utils.Llegeix(reader, out categoriaId, "CATEGORIA");
                                Categoria c = CategoriaBD.GetCategoria(categoriaId);
                                p.Categoria = c;

                                plats.Add(p);
                            }
                            return plats;
                        }
                    }
                }
            }

            catch (Exception ex)
            {
                return new ObservableCollection<Plat>();
            }
        }

        public async static void Insert(Plat p)
        {
            if (p == null) return;
            DbTransaction trans = null;
            try
            {
                using (ContextDB context = new ContextDB())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();
                        using (DbCommand consulta = connexio.CreateCommand())
                        {
                            trans = connexio.BeginTransaction();
                            consulta.Transaction = trans;

                            consulta.CommandText = $@"SELECT `PLAT_ID` FROM `plats` ORDER BY `PLAT_ID` DESC LIMIT 1";
                            var tmp = consulta.ExecuteScalar();
                            int plat_id = (int)tmp;
                            if (plat_id <= 0) plat_id = 1;
                            else plat_id++;
                            consulta.CommandText = $@"
                                INSERT INTO `plats`(`PLAT_ID`, `NOM`, `DESCRIPCIO_MD`, `PREU`, `FOTO`, `DISPONIBLE`, `CATEGORIA`) VALUES 
                                (@plat_id,@nom,@descripcio,@preu,FROM_BASE64(@foto),@disponible,@categoria_id)";
                            BD_Utils.CreateParameter(consulta, "plat_id",       plat_id,        DbType.Int32);
                            BD_Utils.CreateParameter(consulta, "nom",           p.Nom,          DbType.String);
                            BD_Utils.CreateParameter(consulta, "descripcio",    p.Descripcio,   DbType.String);
                            BD_Utils.CreateParameter(consulta, "preu",          p.Preu,         DbType.Decimal);
                            //BD_Utils.CreateParameter(consulta, "foto",          p.Foto.UriSource.AbsolutePath,    DbType.String);
                            string fotoS = string.Empty;

                            StorageFile file = await StorageFile.GetFileFromApplicationUriAsync(p.Foto.UriSource);
                            byte[] byteArray;
                            using (var inputStream = await file.OpenSequentialReadAsync())
                            {
                                var readStream = inputStream.AsStreamForRead();

                                byteArray = new byte[readStream.Length];
                                await readStream.ReadAsync(byteArray, 0, byteArray.Length);
                            }


                            fotoS = Convert.ToBase64String(byteArray);
                            byteArray = null;

                            BD_Utils.CreateParameter(consulta, "foto",          fotoS,          DbType.String);
                            BD_Utils.CreateParameter(consulta, "disponible",    p.Disponible,   DbType.Boolean);
                            BD_Utils.CreateParameter(consulta, "categoria_id",  p.Categoria.Id, DbType.Int32);

                            int affectedPlats = consulta.ExecuteNonQuery();
                            if (affectedPlats != 1)
                            {
                                trans.Rollback();
                                return ;
                            }
                            else
                            {
                                trans.Commit();
                                return ;
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                return ;
            }
        }

        public static Plat GetPlat(long platId)
        {
            if (platId <= 0) return new Plat();
            try
            {

                using (ContextDB context = new ContextDB())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {

                            consulta.CommandText =
                        $@"   SELECT * FROM `plats` WHERE PLAT_ID = @id ";
                            BD_Utils.CreateParameter(consulta, "id", platId, DbType.Int32);

                            var reader = consulta.ExecuteReader();
                            Plat p = new Plat();
                            if (reader.Read())
                            {
                                BD_Utils.Llegeix(reader, out p.id, "PLAT_ID");
                                BD_Utils.Llegeix(reader, out p.nom, "NOM");
                                BD_Utils.Llegeix(reader, out p.descripcio, "DESCRIPCIO_MD", null);
                                BD_Utils.Llegeix(reader, out p.preu, "PREU");
                                BD_Utils.Llegeix(reader, out p.fotoBytes, "FOTO");
                                BD_Utils.Llegeix(reader, out p.disponible, "DISPONIBLE");

                                long categoriaId = 0;
                                BD_Utils.Llegeix(reader, out categoriaId, "CATEGORIA");
                                Categoria c = CategoriaBD.GetCategoria(categoriaId);
                                p.Categoria = c;
                            }
                            return p;
                        }
                    }
                }
            }

            catch (Exception ex)
            {
                return new Plat();
            }
        }

        public static bool IsPlatInComanda(Plat p)
        {
            if (p == null || p.Id <= 0) return false;
            try
            {
                using (ContextDB context = new ContextDB())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {

                            consulta.CommandText =
                        $@"   SELECT * FROM `LINIES_COMANDA` WHERE `PLAT` = @plat_id and `ESTAT` = `EN_PREPARACIO`";
                            BD_Utils.CreateParameter(consulta, "plat_id",  p.Id, DbType.Int32);

                            var reader = consulta.ExecuteReader();
                            return reader.Read();
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                return false;
            }
        }
        public static int Delete(Plat p)
        {
            if (p == null || p.Id <= 0) return -1;
            if (IsPlatInComanda(p)) return -2;
            DbTransaction trans = null;
            try
            {
                using (ContextDB context = new ContextDB())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();
                        using (DbCommand consulta = connexio.CreateCommand())
                        {
                            trans = connexio.BeginTransaction();
                            consulta.Transaction = trans;

                            consulta.CommandText = $@"DELETE FROM linies_escandall where PLAT = @platId";
                            BD_Utils.CreateParameter(consulta, "platId", p.Id, DbType.Int32);
                            consulta.ExecuteNonQuery();
                            consulta.CommandText = $@"DELETE FROM plats WHERE PLAT_ID=@platId";
                            int affectedPlats = consulta.ExecuteNonQuery();

                            if (affectedPlats != 1)
                            {
                                trans.Rollback();
                                return -1;
                            }
                            else
                            {
                                trans.Commit();
                                return 0;
                            }

                        }
                    }
                }
            }
            catch (Exception ex)
            {
                return -2;
            }

        }
    }
}
