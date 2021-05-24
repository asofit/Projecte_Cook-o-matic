using BD.Model;
using System;
using System.Collections.Generic;
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

// La plantilla de elemento Control de usuario está documentada en https://go.microsoft.com/fwlink/?LinkId=234236

namespace Carta_i_Mural.View
{
    public sealed partial class UILiniaComanda : UserControl
    {
        public UILiniaComanda()
        {
            this.InitializeComponent();
        }


        public LiniaComanda CurrentLiniaComanda
        {
            get { return (LiniaComanda)GetValue(CurrentLiniaComandaProperty); }
            set { SetValue(CurrentLiniaComandaProperty, value); }
        }

        // Using a DependencyProperty as the backing store for MyProperty.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty CurrentLiniaComandaProperty =
            DependencyProperty.Register("CurrentLiniaComanda", typeof(LiniaComanda), typeof(UILiniaComanda), new PropertyMetadata(new LiniaComanda()));

        private async void Button_Click(object sender, RoutedEventArgs e)
        {
            if (CurrentLiniaComanda.Estat.Equals(ESTAT.EN_PREPARACIO))
            {
                if (CurrentLiniaComanda.Comanda.GetLiniesEnPreparacio() == 1)
                {
                    ContentDialog cd = new ContentDialog()
                    {
                        Content = "En marcar aquesta línia com a preparada, confirma que la comanda sencera està llesta.\n Hi està d'acord?",
                        PrimaryButtonText = "Sí",
                        CloseButtonText = "Cancel·lar"
                    };

                    ContentDialogResult cdr = await cd.ShowAsync();
                    if (cdr.Equals(ContentDialogResult.Primary))
                    {
                        CurrentLiniaComanda.Preparada();
                    }
                }
                else
                {
                    CurrentLiniaComanda.Preparada();
                }
            }
        }
    }
}
