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
    public sealed partial class UICategoria : UserControl
    {
        public UICategoria()
        {
            this.InitializeComponent();
        }



        public Categoria CurrentCategoria
        {
            get { return (Categoria)GetValue(CurrentCategoriaProperty); }
            set { SetValue(CurrentCategoriaProperty, value); }
        }

        // Using a DependencyProperty as the backing store for CurrentCategoria.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty CurrentCategoriaProperty =
            DependencyProperty.Register("CurrentCategoria", typeof(Categoria), typeof(UICategoria), new PropertyMetadata(new Categoria()));


    }
}
