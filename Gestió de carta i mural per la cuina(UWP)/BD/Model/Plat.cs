using BD.BDModel;
using BD.Utilities;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.Storage.Streams;
using Windows.UI.Xaml.Media.Imaging;

namespace BD.Model
{
    public class Plat
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

        public long Id { get => id; set => id = value; }
        public string Nom { get => nom; set => nom = value; }
        public string Descripcio { get => descripcio; set => descripcio = value; }
        public decimal Preu { get => preu; set => preu = value; }
        public byte[] FotoBytes { get => fotoBytes; 
            set
            {
                fotoBytes = value;
                CarregaFoto();
            }
        }

        public BitmapImage Foto { 
            get => foto;
            set 
            {
                foto = value;
                //if (foto!=null) CarregaBytes();
                //else fotoBytes = new byte[Constants.MAX_BYTES_ARRAY_PLAT];
            } }


        public Categoria Categoria { get => categoria; set => categoria = value; }
        public bool Disponible { get => disponible; set => disponible = value; }

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
    }
}
