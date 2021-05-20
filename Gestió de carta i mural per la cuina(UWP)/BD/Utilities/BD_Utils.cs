using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Common;
using System.Globalization;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Text;
using System.Threading.Tasks;
using Windows.Storage.Streams;
using Windows.UI;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Media.Imaging;

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

        public static void Llegeix(DbDataReader reader, out int valor, string nomColumna, int valorPerDefecte = 0)
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

        public static void Llegeix(DbDataReader reader, out byte[] valor, string nomColumna)
        {
            valor = new byte[16777216];
            int ordinal = reader.GetOrdinal(nomColumna);
            if (!reader.IsDBNull(ordinal))
            {
                Type t = reader.GetFieldType(reader.GetOrdinal(nomColumna));
                reader.GetBytes(ordinal,0, valor, 0, 16777216);
            }
        }

        public static void Llegeix(DbDataReader reader, out bool valor, string nomColumna, bool valorPerDefecte = false)
        {
            valor = valorPerDefecte;
            int ordinal = reader.GetOrdinal(nomColumna);
            if (!reader.IsDBNull(ordinal))
            {
                Type t = reader.GetFieldType(reader.GetOrdinal(nomColumna));
                valor = reader.GetBoolean(ordinal);
            }
        }

        public static void Llegeix(DbDataReader reader, out long valor, string nomColumna, long valorPerDefecte = 0)
        {
            valor = valorPerDefecte;
            int ordinal = reader.GetOrdinal(nomColumna);
            if (!reader.IsDBNull(ordinal))
            {
                Type t = reader.GetFieldType(reader.GetOrdinal(nomColumna));
                valor = reader.GetInt32(ordinal);
            }
        }

        public static void Llegeix(DbDataReader reader, out SolidColorBrush valor, string nomColumna, SolidColorBrush valorPerDefecte)
        {
            valor = valorPerDefecte;
            int ordinal = reader.GetOrdinal(nomColumna);
            if (!reader.IsDBNull(ordinal))
            {
                Type t = reader.GetFieldType(reader.GetOrdinal(nomColumna));
                string valorST = reader.GetString(ordinal);
                valor = GetBrushFromHex(valorST);
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

        public static SolidColorBrush GetBrushFromHex(string hex)
        {
            byte r = byte.Parse(hex.Substring(0, 2), NumberStyles.HexNumber);
            byte g = byte.Parse(hex.Substring(2, 2), NumberStyles.HexNumber);
            byte b = byte.Parse(hex.Substring(4, 2), NumberStyles.HexNumber);

            Color color = Color.FromArgb(255, r, g, b);
            SolidColorBrush br = new SolidColorBrush(color);

            string colorST = br.Color.ToString();

            return new SolidColorBrush(color);
        }
    }
}
