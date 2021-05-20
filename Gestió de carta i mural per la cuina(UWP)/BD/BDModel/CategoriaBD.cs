using BD.Model;
using BD.Utilities;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BD.BDModel
{
    public class CategoriaBD
    {
        public static ObservableCollection<Categoria> GetLlistaCategories()
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
                        $@"   SELECT * FROM `categories` ORDER BY `CATEGORIA_ID` ";

                            var reader = consulta.ExecuteReader();
                            ObservableCollection<Categoria> categories = new ObservableCollection<Categoria>();
                            while (reader.Read())
                            {
                                Categoria c = new Categoria();
                                BD_Utils.Llegeix(reader, out c.id, "CATEGORIA_ID");
                                BD_Utils.Llegeix(reader, out c.nom, "NOM");
                                BD_Utils.Llegeix(reader, out c.color, "COLOR",null);

                                categories.Add(c);
                            }
                            return categories;
                        }
                    }
                }
            }

            catch (Exception ex)
            {
                return new ObservableCollection<Categoria>();
            }
        }

        public static Categoria GetCategoria(long categoriaId)
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
                        $@"   SELECT * FROM `categories` WHERE `CATEGORIA_ID` =  @categoriaId";
                            BD_Utils.CreateParameter(consulta, "categoriaId", categoriaId, DbType.Int32);

                            var reader = consulta.ExecuteReader();
                            Categoria c = null;
                            if (reader.Read())
                            {
                                c = new Categoria();
                                BD_Utils.Llegeix(reader, out c.id, "CATEGORIA_ID");
                                BD_Utils.Llegeix(reader, out c.nom, "NOM");
                                BD_Utils.Llegeix(reader, out c.color, "COLOR", null);
                            }
                            return c;
                        }
                    }
                }
            }

            catch (Exception ex)
            {
                return new Categoria();
            }
        }
    }
}
