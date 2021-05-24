using BD.BDModel;
using BD.Model;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Timers;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Core;
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
    public sealed partial class MuralPage : Page
    {
        Timer t;
        int selectedIndexComanda = 0;
        public MuralPage()
        {
            this.InitializeComponent();
        }

        private void Page_Loaded(object sender, RoutedEventArgs e)
        {
            t = new Timer(2000);
            t.Elapsed += StartTimerComandes;
            t.Start();
        }
        private void Page_Unloaded(object sender, RoutedEventArgs e)
        {
            t.Stop();
        }
        private void StartTimerComandes(object sender, ElapsedEventArgs e)
        {
            try
            {
                Dispatcher.RunAsync(
                    CoreDispatcherPriority.High,
                    () =>
                    {
                        RefreshComandes();
                    }
                    );
            }
            catch(Exception ex)
            {

            }
        }

        private void RefreshComandes()
        {
            lsvComandes.ItemsSource = ComandaBD.GetLlistaComandesPendents();
            if (selectedIndexComanda >= 0 && ((ObservableCollection<Comanda>)lsvComandes.ItemsSource).Count < selectedIndexComanda) lsvComandes.SelectedIndex = selectedIndexComanda;
        }

        private void lsvComandes_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (lsvComandes.SelectedIndex >= 0) selectedIndexComanda = lsvComandes.SelectedIndex;
        }
    }
}
