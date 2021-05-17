using Carta_i_Mural.Model;
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
    public sealed partial class UIPlat : UserControl
    {
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
            DependencyProperty.Register("CurrentPlat", typeof(Plat), typeof(UIPlat), new PropertyMetadata(new Plat()));


    }
}
