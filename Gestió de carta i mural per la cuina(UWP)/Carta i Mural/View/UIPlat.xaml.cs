using BD.Model;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// La plantilla de elemento Control de usuario está documentada en https://go.microsoft.com/fwlink/?LinkId=234236

namespace Carta_i_Mural.View
{
    public sealed partial class UIPlat : UserControl
    {
        private SolidColorBrush dispColour = new SolidColorBrush(Colors.Green);
        private SolidColorBrush noDispColour = new SolidColorBrush(Colors.Red);
        public UIPlat()
        {
            this.InitializeComponent();
        }

        public Plat CurrentPlat
        {
            get { return (Plat)GetValue(CurrentPlatProperty); }
            set { SetValue(CurrentPlatProperty, value); }
        }

        // Using a DependencyProperty as the backing store for CurrentPlat.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty CurrentPlatProperty =
            DependencyProperty.Register("CurrentPlat", typeof(Plat), typeof(UIPlat), new PropertyMetadata(new Plat(),CurrentPlatChangedCallback));

        private static void CurrentPlatChangedCallback(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            ((UIPlat)d).CurrentPlatChanged(e);
        }

        private async void CurrentPlatChanged(DependencyPropertyChangedEventArgs e)
        {
            if (CurrentPlat != null)
            {
                await CurrentPlat.CarregaFoto();
                imgPlat.Source = CurrentPlat.Foto;

                brdPlat.BorderBrush = CurrentPlat.Disponible ? dispColour : noDispColour;
            }
        }
    }
}
