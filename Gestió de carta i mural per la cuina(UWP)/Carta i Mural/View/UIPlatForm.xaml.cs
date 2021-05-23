using BD.Model;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Threading.Tasks;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.Storage;
using Windows.Storage.Pickers;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;

// La plantilla de elemento Control de usuario está documentada en https://go.microsoft.com/fwlink/?LinkId=234236

namespace Carta_i_Mural.View
{
    public sealed partial class UIPlatForm : UserControl
    {
        public UIPlatForm()
        {
            this.InitializeComponent();
        }


        public Plat CurrentPlat
        {
            get { return (Plat)GetValue(CurrentPlatProperty); }
            set { SetValue(CurrentPlatProperty, value); }
        }

        // Using a DependencyProperty as the backing store for MyProperty.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty CurrentPlatProperty =
            DependencyProperty.Register("CurrentPlat", typeof(Plat), typeof(UIPlatForm), new PropertyMetadata(new Plat()));

        private async void Button_Click(object sender, RoutedEventArgs e)
        {
            FileOpenPicker fp = new FileOpenPicker();
            fp.FileTypeFilter.Add(".jpg");
            fp.FileTypeFilter.Add(".png");
            fp.FileTypeFilter.Add("*");
            fp.SuggestedStartLocation = PickerLocationId.PicturesLibrary;

            StorageFile sf = await fp.PickSingleFileAsync();
            if (sf != null)
            {
                // Cerca la carpeta de dades de l'aplicació, dins de ApplicationData
                var folder = ApplicationData.Current.LocalFolder;
                // Dins de la carpeta de dades, creem una nova carpeta "icons"
                var iconsFolder = await folder.CreateFolderAsync("icons", CreationCollisionOption.OpenIfExists);
                // Creem un nom usant la data i hora, de forma que no poguem repetir noms.
                string name = (DateTime.Now).ToString("yyyyMMddhhmmss") + "_" + sf.Name;
                // Copiar l'arxiu triat a la carpeta indicada, usant el nom que hem muntat
                StorageFile copiedFile = await sf.CopyAsync(iconsFolder, name);
                // ..... YOUR CODE HERE ...........
                
                //var stream = await sf.OpenStreamForReadAsync();
                //var bytes = new byte[(int)stream.Length];
                //stream.Read(bytes, 0, (int)stream.Length);

                //CurrentPlat.fotoBytes = bytes;
                CurrentPlat.Foto = new BitmapImage(new Uri(copiedFile.Path));
                //imgPlat.Source = CurrentPlat.Foto;
            }
        }

        private void uiPlatForm_Loaded(object sender, RoutedEventArgs e)
        {
            cboCategoria.ItemsSource = Categoria.getListCategories();
        }

        private void TextBox_TextChanged(object sender, TextChangedEventArgs e)
        {
            string s = ((TextBox)sender).Text;
            
            if (!(Decimal.TryParse(s, out CurrentPlat.preu)))
            {
                ((TextBox)sender).Text = "0";
            }
        }
    }
}
