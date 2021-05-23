using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BD.Model
{
    public class Comanda
    {
        public int id;
        public DateTime data_comanda;
        public int taula_id;
        private ObservableCollection<LiniaComanda> linies;

        public Comanda()
        {
        }

        public int Id { get => id; set => id = value; }
        public DateTime Data_comanda { get => data_comanda; set => data_comanda = value; }
        public int Taula_id { get => taula_id; set => taula_id = value; }
        public ObservableCollection<LiniaComanda> Linies { get => linies; set => linies = value; }
    }
}
