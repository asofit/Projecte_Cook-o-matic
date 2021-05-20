using BD.BDModel;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI;
using Windows.UI.Xaml.Media;

namespace BD.Model
{
    public class Categoria
    {
        public long id;
        public string nom;
        public SolidColorBrush color;

        private static ObservableCollection<Categoria> categories;

        public Categoria()
        {

        }

        public Categoria(int id, string nom, SolidColorBrush color)
        {
            this.id = id;
            this.nom = nom;
            this.color = color;
        }

        public long Id { get => id; set => id = value; }
        public string Nom { get => nom; set => nom = value; }
        public SolidColorBrush Color { get => color; set => color = value; }

        public static ObservableCollection<Categoria> getListCategories()
        {
            categories = CategoriaBD.GetLlistaCategories();
            return categories;
        }

        public override bool Equals(object obj)
        {
            return obj is Categoria categoria &&
                   Id == categoria.Id;
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(Id);
        }

        public override string ToString()
        {
            return this.Nom;
        }
    }
}
