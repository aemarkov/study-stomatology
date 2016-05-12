using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models
{
    /// <summary>
    /// Зубной техник
	/// Он выполняет наряд-заказы
    /// </summary>
    public class DentalTechnican:ApplicationUser
    {
        /// <summary>
		/// Наряд-зкаказы
		/// </summary>
		public ICollection<Order> Orders { get; set; }


        /////////////////////////////////// КОНСТРУКТОРЫ //////////////////////////////////////////
        public DentalTechnican() { }
        public DentalTechnican(string surname, string name, string middlename = null) : base(surname, name, middlename)
        {
        }
    }
}