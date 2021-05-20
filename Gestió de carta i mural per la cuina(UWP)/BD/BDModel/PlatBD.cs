using BD.Model;
using BD.Utilities;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Data.Common;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

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

        public static bool Insert(Plat p)
        {
            if (p == null) return false;
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
                                ('@plat_id','@nom','@descripcio','@preu',LOAD_FILE(@foto),'@disponible','@categoria_id')";
                            BD_Utils.CreateParameter(consulta, "plat_id",       plat_id,        DbType.Int32);
                            BD_Utils.CreateParameter(consulta, "nom",           p.Nom,          DbType.String);
                            BD_Utils.CreateParameter(consulta, "descripcio",    p.Descripcio,   DbType.String);
                            BD_Utils.CreateParameter(consulta, "preu",          p.Preu,         DbType.Decimal);
                            BD_Utils.CreateParameter(consulta, "foto",          p.Foto.UriSource.AbsolutePath,    DbType.String);
                            BD_Utils.CreateParameter(consulta, "disponible",    p.Disponible,   DbType.Boolean);
                            BD_Utils.CreateParameter(consulta, "categoria_id",  p.Categoria.Id, DbType.Int32);

                            int filesAfectades = consulta.ExecuteNonQuery();
                            if (filesAfectades != 1)
                            {
                                trans.Rollback();
                                return false;
                            }
                            else
                            {
                                trans.Commit();
                                return true;
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                return false;
            }
        }
    }
}
