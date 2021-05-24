using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BD.Model
{
    public class Comanda: IComparable
    {
        public int id;
        public DateTime data_comanda;
        public int taula_id;
        public string cambrer_name;

        private ObservableCollection<LiniaComanda> linies;

        public Comanda()
        {
        }

        public int Id { get => id; set => id = value; }
        public DateTime Data_comanda { get => data_comanda; set => data_comanda = value; }
        public string Data_comanda_string { get { return data_comanda.ToShortTimeString(); }}
        public int Taula_id { get => taula_id; set => taula_id = value; }
        public ObservableCollection<LiniaComanda> Linies { get => linies; set => linies = value; }
        public string Cambrer_name { get => cambrer_name; set => cambrer_name = value; }

        public int CompareTo(object obj)
        {
            if (obj == null) return 1;

            Comanda altraComanda = obj as Comanda;
            if (altraComanda != null)
                return this.Data_comanda.CompareTo(altraComanda.Data_comanda);
            else
                throw new ArgumentException("L'objecte no és una comanda");
        }

        public int GetLiniesEnPreparacio()
        {
            int linies = 0;
            foreach(LiniaComanda l in Linies)
            {
                if (l.Estat.Equals(ESTAT.EN_PREPARACIO)) linies++;
            }
            return linies;
        }
    }
}
