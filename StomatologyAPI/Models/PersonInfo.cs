using StomatologyAPI.Models.Abstract;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models
{
    /// <summary>
    /// Содержит информацию, связанную с человеком: 
    ///  - врач
    ///  - пациент
    ///  - техник
    ///  итп.
    ///  
    /// Содержит:
    ///  - ФИО
    /// </summary>
    [Table("PersonInfo")]
    public class PersonInfo:AbstractModel
    {
        /// <summary>
        /// Фамилия
        /// </summary>
        [Required]
        [MaxLength(64)]
        public string Surname { get; set; }

        /// <summary>
        /// Имя
        /// </summary>
        [Required]
        [MaxLength(64)]
        public string Name { get; set; }

        /// <summary>
        /// Отчество
        /// </summary>
        [MaxLength(64)]
        public string Middlename { get; set; }

        /////////////////////////////////////////////////////////////////////////////////

        /// <summary>
        /// Конструктор по умолчанию
        /// </summary>
        public PersonInfo() { }

        public PersonInfo(string surname, string name, string middlename = null)
        {
            Surname = surname;
            Name = name;
            Middlename = middlename;
        }
    }
}