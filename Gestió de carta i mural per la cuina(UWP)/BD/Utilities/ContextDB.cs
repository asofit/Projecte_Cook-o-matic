using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BD.Utilities
{
    public class ContextDB : DbContext
    {
        protected override void OnConfiguring(
            DbContextOptionsBuilder optionBuilder
            )
        {
            string db = "dam2_sgomez1";
            optionBuilder.UseMySQL(
                "Server=51.68.224.27;Database=" + db + ";UID=dam2-sgomez1;Password=47107354L");
        }
    }
}
