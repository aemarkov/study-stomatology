using StomatologyAPI.Models.Abstract;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models
{
    /// <summary>
    /// То, что может сделать техник (коронка, протез итп)
    /// </summary>
    public class TechnicanProcedure:AbstractModel
    {
        /// <summary>
        /// Название процедуры
        /// </summary>
		[MaxLength(128)]
		[Required]
        public String Name { get; set; }

        /// <summary>
		/// Стоимость процедуры
		/// </summary>
		[Required]
        public decimal Cost { get; set; }
    }
}