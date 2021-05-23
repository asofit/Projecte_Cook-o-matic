using BD.BDModel;
using BD.Utilities;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.Storage.Streams;
using Windows.UI.Xaml.Media.Imaging;

namespace BD.Model
{
    public class Plat: INotifyPropertyChanged
    {
        public long id;
        public string nom;
        public string descripcio;
        public decimal preu;
        public byte[] fotoBytes;
        public BitmapImage foto;
        public bool disponible;
        public Categoria categoria;

        private static ObservableCollection<Plat> plats;

        public event PropertyChangedEventHandler PropertyChanged;

        private void RaisePropertyChanged(string v)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(v));
        }

        public long Id { get => id; set { id = value; RaisePropertyChanged("Id"); } }
        public string Nom { get => nom; set { nom = value; RaisePropertyChanged("Nom"); } }
        public string Descripcio { get => descripcio; set { descripcio = value; RaisePropertyChanged("Descripcio"); } }
        public decimal Preu { get => preu; set { preu = value; RaisePropertyChanged("Preu"); } }
        public byte[] FotoBytes { get => fotoBytes; 
            set
            {
                fotoBytes = value;
                RaisePropertyChanged("FotoBytes");
                CarregaFoto();
                
            }
        }

        public BitmapImage Foto { 
            get => foto;
            set 
            {
                foto = value;
                RaisePropertyChanged("Foto");
                //if (foto!=null) CarregaBytes();
                //else fotoBytes = new byte[Constants.MAX_BYTES_ARRAY_PLAT];
            } }


        public Categoria Categoria { get => categoria; set { categoria = value; RaisePropertyChanged("Categoria"); } }
        public bool Disponible { get => disponible; set { disponible = value; RaisePropertyChanged("Disponible"); } }

        public Plat()
        {
            fotoBytes = new byte[Constants.MAX_BYTES_ARRAY_PLAT];
        }
        public async Task CarregaFoto()
        {
            this.foto = await UI_Utils.ByteArrayToBitmapImageAsync(this.fotoBytes);
        }

        public static ObservableCollection<Plat> getListPlats()
        {
            plats = PlatBD.GetLlistaPlats();
            return plats;
        }

        public override bool Equals(object obj)
        {
            return obj is Plat plat &&
                   Id == plat.Id;
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(Id);
        }

        public static bool SavePlat(Plat p)
        {

            bool inserted = PlatBD.Insert(p);
            return inserted;
        }

        public static int RemovePlat(Plat p)
        {
            int deleted = PlatBD.Delete(p);
            return deleted;
        }
    }
}
