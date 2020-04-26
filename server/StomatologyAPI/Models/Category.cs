using StomatologyAPI.Models.Abstract;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models
{ 
    /// <summary>
    /// Описывают категорию услуг из раздела описания.
    /// Пример категории: терапия, хирургия итп.
    /// Категория содержиться в разделе и содержит в себе
    /// процедуры, разделенные на подкатегории
    /// </summary>
    public class Category: AbstractModel
    {
        /// <summary>
        /// Картинка категории
        /// </summary>
        [MaxLength(255)]
        public string Image { get; set; }

        /// <summary>
        /// Название категории
        /// </summary>
        [MaxLength(64)]
        public string Name { get; set; }

        /// <summary>
        /// Описание категории
        /// </summary>
        public string Description { get; set; }

        //Подкатегории
        private ICollection<Subcategory> subcategories;

        /// <summary>
        /// Список подкатегорий
        /// </summary>
        public ICollection<Subcategory> Subcategories
        {
            get { return this.subcategories ?? (this.subcategories = new List<Subcategory>()); }
            set { subcategories = value; }
        }


        /////////////////////////////////// КОНСТРУКТОРЫ //////////////////////////////////////////

        public Category() { }
        public Category(string name, string description = null)
        {
            Name = name;
            Description = description;
        }
    }
}