using BD.BDModel;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI;
using Windows.UI.Xaml.Media;

namespace BD.Model
{
    public enum ESTAT
    {
        NUL,
        EN_PREPARACIO,
        PREPARADA
    }
    public class LiniaComanda: IComparable, INotifyPropertyChanged
    {
        public int id;
        public Comanda comanda;
        public Plat plat;
        public int quantitat;
        public ESTAT estat;

        public event PropertyChangedEventHandler PropertyChanged;

        public LiniaComanda()
        {
        }

        private void RaisePropertyChanged(string v)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(v));
        }

        public int Id { get => id; set => id = value; }
        public Comanda Comanda { get => comanda; set => comanda = value; }
        public Plat Plat { get => plat; set => plat = value; }
        public int Quantitat { get => quantitat; set => quantitat = value; }
        public ESTAT Estat { get => estat; set { estat = value; RaisePropertyChanged("Estat"); } }
        public SolidColorBrush Color { 
            get 
            { 
                if (Estat.Equals(ESTAT.PREPARADA)) return new SolidColorBrush(Colors.GreenYellow); 
                else return new SolidColorBrush(Colors.Transparent);
            } 
        }

        public int CompareTo(object obj)
        {
            if (obj == null) return 1;

            LiniaComanda altraLinia = obj as LiniaComanda;
            if (altraLinia != null)
                return this.Estat.CompareTo(altraLinia.Estat);
            else
                throw new ArgumentException("L'objecte no és una línia de comanda");
        }

        public void Preparada()
        {
            ComandaBD.SetLiniaComandaPreparada(this);
        }

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
