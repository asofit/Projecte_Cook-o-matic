using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BD.Model
{
    public enum ESTAT
    {
        EN_PREPARACIO,
        PREPARADA
    }
    public class LiniaComanda
    {
        public int id;
        public Comanda comanda;
        public Plat plat;
        public int quantitat;
        public ESTAT estat;

        public LiniaComanda()
        {
        }

        public int Id { get => id; set => id = value; }
        public Comanda Comanda { get => comanda; set => comanda = value; }
        public Plat Plat { get => plat; set => plat = value; }
        public int Quantitat { get => quantitat; set => quantitat = value; }
        public ESTAT Estat { get => estat; set => estat = value; }

        public override bool Equals(object obj)
        {
            var comanda = obj as LiniaComanda;
            return comanda != null &&
                   Id == comanda.Id &&
                   EqualityComparer<Comanda>.Default.Equals(Comanda, comanda.Comanda);
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(Id, Comanda);
        }
    }
}
