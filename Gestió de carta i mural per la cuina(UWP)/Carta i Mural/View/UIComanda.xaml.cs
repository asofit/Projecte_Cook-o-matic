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
    public sealed partial class UIComanda : UserControl
    {
        public UIComanda()
        {
            this.InitializeComponent();
        }



        public Comanda CurrentComanda
        {
            get { return (Comanda)GetValue(CurrentComandaProperty); }
            set { SetValue(CurrentComandaProperty, value); }
        }

        // Using a DependencyProperty as the backing store for MyProperty.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty CurrentComandaProperty =
            DependencyProperty.Register("CurrentComanda", typeof(Comanda), typeof(UIComanda), new PropertyMetadata(new Comanda()));


    }
}
