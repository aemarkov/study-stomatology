using Microsoft.AspNet.Identity.EntityFramework;
using StomatologyAPI.Infrastructure;
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
            Database.SetInitializer(new DbInitializer());
        }

        protected override void OnModelCreating(DbModelBuilder builder)
        {
            base.OnModelCreating(builder);

            builder.Entity<Patient>()
                .HasMany(x => x.Visits);

            builder.Entity<Patient>()
                .HasOptional(x => x.ApplicationUser);
            builder.Entity<Patient>()
                .HasRequired(x => x.PersonInfo);

            builder.Entity<Doctor>()
                .HasRequired(x => x.ApplicationUser);
            builder.Entity<Doctor>()
                .HasRequired(x => x.PersonInfo);

            builder.Entity<DentalTechnican>()
                .HasRequired(x => x.ApplicationUser);
            builder.Entity<DentalTechnican>()
                .HasRequired(x => x.PersonInfo);


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

        public DbSet<Patient> Patients { get; set; }
        public DbSet<Doctor> Doctors { get; set; }
        public DbSet<DentalTechnican> DentalTechnicans { get; set; }
    }
}