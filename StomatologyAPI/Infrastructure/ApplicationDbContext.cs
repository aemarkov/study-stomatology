using Microsoft.AspNet.Identity.EntityFramework;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models
{
    /// <summary>
    /// Контекст базы данных
    /// </summary>
    public class ApplicationDbContext : IdentityDbContext<ApplicationUser, CustomRole, int, CustomUserLogin, CustomUserRole, CustomUserClaim>
    {
        public ApplicationDbContext()
            : base("SQLExpress")
        {
        }

        protected override void OnModelCreating(DbModelBuilder builder)
        {
            base.OnModelCreating(builder);
        }

        public static ApplicationDbContext Create()
        {
            return new ApplicationDbContext();
        }


        public DbSet<Category> Categories { get; set; }
        public DbSet<Subcategory> Subcategories { get; set; }
        public DbSet<Procedure> Procedures { get; set; }
        public DbSet<TechnicanProcedure> TechnicanProcedures { get; set; }
        public DbSet<PatientVisit> PatientVisits { get; set; }
        public DbSet<Order> Orders { get; set; }
        public DbSet<ToothWork> ToothWorkds { get; set; }
        public DbSet<ClinicInfo> ClinincInfo { get; set; }
    }
}