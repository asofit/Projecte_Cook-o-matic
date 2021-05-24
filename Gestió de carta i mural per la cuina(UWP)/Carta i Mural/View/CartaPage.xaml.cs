using BD.Model;
using System;
using System.Collections.ObjectModel;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// La plantilla de elemento Página en blanco está documentada en https://go.microsoft.com/fwlink/?LinkId=234238

namespace Carta_i_Mural.View
{
    /// <summary>
    /// Una página vacía que se puede usar de forma independiente o a la que se puede navegar dentro de un objeto Frame.
    /// </summary>
    public sealed partial class CartaPage : Page
    {
        private ObservableCollection<Plat> allPlats;
        private ObservableCollection<Plat> selectedPlats = new ObservableCollection<Plat>();

        private bool platSelected = false;

        public CartaPage()
        {
            this.InitializeComponent();
        }
        private void Page_Loaded(object sender, RoutedEventArgs e)
        {
            allPlats = Plat.getListPlats();
            CargaLlistes();

            btnDelPlat.IsEnabled = false;
            grdPlatForm.Visibility = Visibility.Collapsed;
        }

        private void CargaLlistes()
        {
            lsvCategories.ItemsSource = Categoria.getListCategories();
            RefreshLlistaPlats();
        }

        private void RefreshLlistaPlats()
        {
            selectedPlats.Clear();

            Categoria c = (Categoria)lsvCategories.SelectedItem;
            string s = txbFiltrePlats.Text;
            foreach(Plat p in allPlats)
            {
                // Afegim els plats que compleixin els filtres
                // Estructura: (filtre && filtre && ...)
                // Filtre = filtreActiu || condicionsDelFiltre
                if ((c == null || (p.Categoria != null && p.Categoria.Equals(c))) &&
                    ((s == null || s.Length <= 0) || (p.Nom.ToLower().IndexOf(s.ToLower()) >= 0)))
                {
                    selectedPlats.Add(p);
                }
            }

            grvPlats.ItemsSource = selectedPlats;
        }

        private void lsvCategories_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            RefreshLlistaPlats();
        }
        private void grvPlats_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            Plat p = (Plat)grvPlats.SelectedItem;
            platSelected = (p != null);

            btnDelPlat.IsEnabled = platSelected;
        }
        private void TextBox_TextChanging(TextBox sender, TextBoxTextChangingEventArgs args)
        {
            RefreshLlistaPlats();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            lsvCategories.SelectedIndex = -1;
            RefreshLlistaPlats();
        }

        // +
        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            grdPlatForm.Visibility = Visibility.Visible;
            uiPlatForm.CurrentPlat = new Plat();

            btnAddPlat.IsEnabled = false;
        }

        // -
        private async void Button_Click_2(object sender, RoutedEventArgs e)
        {
            Plat p = (Plat)grvPlats.SelectedItem;

            int deleted = Plat.RemovePlat(p);

            if (deleted==0)
            {
                grdPlatForm.Visibility = Visibility.Collapsed;
                btnAddPlat.IsEnabled = true;
                allPlats = Plat.getListPlats();
                CargaLlistes();
            }
            else
            {
                string error = "";
                switch (deleted)
                {
                    case -1:
                        error = "Plat invàlid o no existent a la BD. Consulti amb el servei tècnic.";
                        break;
                    case -2:
                        error = "El plat seleccionat forma part d'alguna comanda i no es pot eliminar.";
                        break;
                    default:
                        error = "Error desconegut en intentar esborrar el plat seleccionat. Consulti amb el servei tècnic.";
                        break;
                }
                ContentDialog cd = new ContentDialog()
                {
                    Content = error,
                    CloseButtonText = "Ok"
                };

                ContentDialogResult cdr = await cd.ShowAsync();
            }
        }

        // Guardar
        private async void Button_Click_3(object sender, RoutedEventArgs e)
        {
            Plat p = uiPlatForm.CurrentPlat;

            bool saved = Plat.SavePlat(p);

            if (saved)
            {
                grdPlatForm.Visibility = Visibility.Collapsed;
                btnAddPlat.IsEnabled = true;
                allPlats = Plat.getListPlats();
                CargaLlistes();
            }
            else
            {
                ContentDialog cd = new ContentDialog()
                {
                    Content = "No ha sigut possible guardar el plat. Recordi que tots els camps són obligatoris.",
                    CloseButtonText = "Ok"
                };

                ContentDialogResult cdr = await cd.ShowAsync();
            }

        }

        // Cancel·lar
        private async void Button_Click_4(object sender, RoutedEventArgs e)
        {
            ContentDialog cd = new ContentDialog() 
            { 
                Content="Vols cancel·lar els canvis?",
                PrimaryButtonText="Sí",
                CloseButtonText="No"
            };

            ContentDialogResult cdr = await cd.ShowAsync();

            if (cdr.Equals(ContentDialogResult.Primary))
            {
                grdPlatForm.Visibility = Visibility.Collapsed;
                uiPlatForm.CurrentPlat = new Plat();
                btnAddPlat.IsEnabled = true;
                allPlats = Plat.getListPlats();
                CargaLlistes();
            }

        }
    }
}
