using StomatologyAPI.Models.Abstract;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models
{
    /// <summary>
	/// Элемент наряд-заказа для конкретного зуба
	/// </summary>
    public class ToothWork:AbstractModel
    {
        /// <summary>
		/// Номер зуба
		/// </summary>
		[Required]
        public int ToothNo { get; set; }

        //Выполненная процедура
        [Required]
        public int ProcedureId { get; set; }
        public TechnicanProcedure Procedure { get; set; }

        /// <summary>
        /// Связь с наряд-заказом
        /// </summary>
        [Required]
        public int OrderId { get; set; }
    }
}