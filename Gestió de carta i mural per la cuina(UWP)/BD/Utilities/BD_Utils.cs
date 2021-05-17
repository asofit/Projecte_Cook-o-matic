using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Common;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI;

namespace BD.Utilities
{
    public class BD_Utils
    {
        public static void CreateParameter(
            DbCommand consulta,
            string nom, object valor, DbType tipus)
        {
            DbParameter param = consulta.CreateParameter();
            param.ParameterName = nom;
            param.Value = valor;
            param.DbType = tipus;
            consulta.Parameters.Add(param);
        }

        public static void Llegeix(DbDataReader reader, out int valor, string nomColumna, int valorPerDefecte = -1)
        {
            valor = valorPerDefecte;
            int ordinal = reader.GetOrdinal(nomColumna);
            if (!reader.IsDBNull(ordinal))
            {
                Type t = reader.GetFieldType(reader.GetOrdinal(nomColumna));
                valor = reader.GetInt32(ordinal);
            }
        }
        public static void Llegeix(DbDataReader reader, out char valor, string nomColumna, char valorPerDefecte)
        {
            valor = valorPerDefecte;
            int ordinal = reader.GetOrdinal(nomColumna);
            if (!reader.IsDBNull(ordinal))
            {
                Type t = reader.GetFieldType(reader.GetOrdinal(nomColumna));
                valor = reader.GetChar(ordinal);
            }
        }
        public static void Llegeix(DbDataReader reader, out string valor, string nomColumna, string valorPerDefecte = "")
        {
            valor = valorPerDefecte;
            int ordinal = reader.GetOrdinal(nomColumna);
            if (!reader.IsDBNull(ordinal))
            {
                Type t = reader.GetFieldType(reader.GetOrdinal(nomColumna));

                valor = reader.GetString(ordinal);
            }
        }
        public static void Llegeix(DbDataReader reader, out DateTime valor, string nomColumna, DateTime valorPerDefecte = new DateTime())
        {
            valor = valorPerDefecte;
            int ordinal = reader.GetOrdinal(nomColumna);
            if (!reader.IsDBNull(ordinal))
            {
                Type t = reader.GetFieldType(reader.GetOrdinal(nomColumna));

                valor = reader.GetDateTime(ordinal);
            }
        }

        public static void Llegeix(DbDataReader reader, out Decimal valor, string nomColumna, Decimal valorPerDefecte = 0m)
        {
            valor = valorPerDefecte;
            int ordinal = reader.GetOrdinal(nomColumna);
            if (!reader.IsDBNull(ordinal))
            {
                Type t = reader.GetFieldType(reader.GetOrdinal(nomColumna));

                valor = reader.GetDecimal(ordinal);
            }
        }
        public static Int32 ColorToInt32(Color colour)
        {
            if (colour != null)
            {
                byte[] bites = new byte[] { colour.A, colour.R, colour.G, colour.B };
                return BitConverter.ToInt32(bites, 0);
            }
            return 0;
        }
        public static Color Int32ToColor(Int32 num)
        {
            if (num != 0)
            {
                byte[] numBites = BitConverter.GetBytes(num);
                return Color.FromArgb(numBites[0], numBites[1], numBites[2], numBites[3]);
            }
            return Colors.Transparent;
        }
    }
}
