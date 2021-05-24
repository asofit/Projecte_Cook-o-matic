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
    public class ComandaBD
    {
        public static ObservableCollection<Comanda> GetLlistaComandesPendents()
        {
            DbTransaction trans = null;
            try
            {

                using (ContextDB context = new ContextDB())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {
                            trans = connexio.BeginTransaction();
                            consulta.Transaction = trans;

                            consulta.CommandText =
                        $@"   SELECT c.*, ca.NOM, ca.COGNOM1 FROM `comandes` c join `cambrers` ca on ca.CAMBRER_ID = c.CAMBRER 
                              WHERE `COMANDA_ID` IN (SELECT lc.COMANDA from `linies_comanda` lc where lc.COMANDA = c.COMANDA_ID and lc.ESTAT = 'EN_PREPARACIO') ";

                            var reader = consulta.ExecuteReader();
                            ObservableCollection<Comanda> comandes = new ObservableCollection<Comanda>();
                            while (reader.Read())
                            {
                                Comanda c = new Comanda();
                                BD_Utils.Llegeix(reader, out c.id, "COMANDA_ID");
                                BD_Utils.Llegeix(reader, out c.data_comanda, "DATA_COMANDA");
                                BD_Utils.Llegeix(reader, out c.taula_id, "TAULA");

                                BD_Utils.Llegeix(reader, out c.cambrer_name, "NOM");
                                string cambrerName = "";
                                BD_Utils.Llegeix(reader, out cambrerName, "COGNOM1");
                                c.Cambrer_name += " " + cambrerName;


                                comandes.Add(c);
                            }

                            connexio.Close();

                            foreach (Comanda c in comandes)
                            {
                                c.Linies = GetLiniesComanda(c);
                            }

                            comandes = new ObservableCollection<Comanda>(comandes.OrderBy(i => i));
                            return comandes;
                        }
                    }
                }
            }

            catch (Exception ex)
            {
                return new ObservableCollection<Comanda>();
            }
        }

        public static bool SetLiniaComandaPreparada(LiniaComanda liniaComanda)
        {
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

                            consulta.CommandText = $@" UPDATE `linies_comanda` SET `ESTAT`='PREPARADA' WHERE COMANDA=@comanda_id and LINIA_COM_ID=@linia_id";
                            BD_Utils.CreateParameter(consulta, "linia_id", liniaComanda.Id, DbType.Int32);
                            BD_Utils.CreateParameter(consulta, "comanda_id", liniaComanda.Comanda.Id, DbType.Int32);

                            int filesAfectades = consulta.ExecuteNonQuery();
                            if (filesAfectades != 1)
                            {
                                trans.Rollback();
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

            }

            return false;
        }

        public static ObservableCollection<LiniaComanda> GetLiniesComanda(Comanda c)
        {
            if (c.Id <= 0) return new ObservableCollection<LiniaComanda>();
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
                        $@"   SELECT * FROM `linies_comanda` WHERE COMANDA = @id ";
                            BD_Utils.CreateParameter(consulta, "id", c.Id, DbType.Int32);

                            var reader = consulta.ExecuteReader();
                            ObservableCollection<LiniaComanda> linies = new ObservableCollection<LiniaComanda>();
                            while (reader.Read())
                            {
                                LiniaComanda l = new LiniaComanda();
                                BD_Utils.Llegeix(reader, out l.id, "LINIA_COM_ID");
                                BD_Utils.Llegeix(reader, out l.quantitat, "QUANTITAT");
                                BD_Utils.Llegeix(reader, out l.estat, "ESTAT");

                                long platId = 0;
                                BD_Utils.Llegeix(reader, out platId, "PLAT");
                                Plat p = PlatBD.GetPlat(platId);
                                l.Plat = p;

                                l.Comanda = c;

                                linies.Add(l);
                            }

                            linies = new ObservableCollection<LiniaComanda>(linies.OrderBy(i => i));
                            connexio.Close();
                            return linies;
                        }
                    }
                }
            }

            catch (Exception ex)
            {
                return new ObservableCollection<LiniaComanda>();
            }
        }
    }
}