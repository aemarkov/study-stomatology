using StomatologyAPI.Models.Abstract;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models
{
    /// <summary>
	/// Модель подкатегории
	/// Подкатегория - разделение процедур внутри
	/// категории на небольшие группы.
	/// Например: 
	/// 
	/// Терапия:
	///  - Анестезия:
	///    - а
	///    - б
	///    - в
	/// </summary>
    public class Subcategory:AbstractModel
    {
        /// <summary>
        /// Название подкатегории
        /// </summary>
        public string Name { get; set; }


        /// <summary>
        /// Связь с категорией
        /// </summary>
        [Required]
        public int CategoryId { get; set; }

        //Процедуры
        private ICollection<Procedure> procedures;

        /// <summary>
        /// список процедур
        /// </summary>
        public ICollection<Procedure> Procedures
        {
            get { return procedures ?? (procedures = new List<Procedure>()); }
            set { procedures = value; }
        }

        /////////////////////////////////// КОНСТРУКТОРЫ //////////////////////////////////////////
        public Subcategory() { }
        public Subcategory(string name)
        {
            Name = name;
        }
    }
}