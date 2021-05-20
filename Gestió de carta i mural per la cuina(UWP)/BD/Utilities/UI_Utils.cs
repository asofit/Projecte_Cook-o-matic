using BD.Model;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.Graphics.Imaging;
using Windows.Storage.Streams;
using Windows.UI.Xaml.Media.Imaging;

namespace BD.Utilities
{
    public class UI_Utils
    {
        public static async Task<BitmapImage> ByteArrayToBitmapImageAsync(byte[] bytes)
        {
            bool voidArray = true;
            for(int i = 0; i < bytes.Length && voidArray; i++)
            {
                if (bytes[i] != 0)
                {
                    voidArray = false;
                }
            }
            if (voidArray) return null;
            else
            {
                using (InMemoryRandomAccessStream stream = new InMemoryRandomAccessStream())
                {
                    using (DataWriter writer = new DataWriter(stream.GetOutputStreamAt(0)))
                    {
                        writer.WriteBytes(bytes);
                        await writer.StoreAsync();
                    }
                    BitmapImage img = new BitmapImage();
                    await img.SetSourceAsync(stream);

                    return img;
                }
            }
        }
    }
}
