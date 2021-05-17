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
            string db = "COOK_O_MATIC_BD";
            optionBuilder.UseMySQL(
                "Server=localhost;Database=" + db + ";UID=root;Password=");
        }
    }
}
