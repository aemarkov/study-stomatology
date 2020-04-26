namespace StomatologyAPI.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class FUUUUUUCK : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Categories",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Image = c.String(maxLength: 255),
                        Name = c.String(maxLength: 64),
                        Description = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Subcategories",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        CategoryId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Categories", t => t.CategoryId, cascadeDelete: true)
                .Index(t => t.CategoryId);
            
            CreateTable(
                "dbo.Procedures",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        Description = c.String(),
                        SubcategoryId = c.Int(nullable: false),
                        Cost = c.Int(nullable: false),
                        PatientVisit_Id = c.Int(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Subcategories", t => t.SubcategoryId, cascadeDelete: true)
                .ForeignKey("dbo.PatientVisits", t => t.PatientVisit_Id)
                .Index(t => t.SubcategoryId)
                .Index(t => t.PatientVisit_Id);
            
            CreateTable(
                "dbo.ClinicInfoes",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(nullable: false),
                        PhoneNumber = c.String(),
                        Email = c.String(),
                        Adress = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.DentalTechnicans",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        ApplicationUserId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.AspNetUsers", t => t.ApplicationUserId, cascadeDelete: true)
                .Index(t => t.ApplicationUserId);
            
            CreateTable(
                "dbo.Orders",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Date = c.DateTime(),
                        IsFinished = c.Boolean(nullable: false),
                        Annotation = c.String(),
                        PatientId = c.Int(nullable: false),
                        ClinicInfoId = c.Int(nullable: false),
                        DentalTechnican_Id = c.Int(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.ClinicInfoes", t => t.ClinicInfoId, cascadeDelete: true)
                .ForeignKey("dbo.DentalTechnicans", t => t.DentalTechnican_Id)
                .ForeignKey("dbo.Patients", t => t.PatientId, cascadeDelete: true)
                .Index(t => t.PatientId)
                .Index(t => t.ClinicInfoId)
                .Index(t => t.DentalTechnican_Id);
            
            CreateTable(
                "dbo.ToothWorks",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        ToothNo = c.Int(nullable: false),
                        ProcedureId = c.Int(nullable: false),
                        OrderId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.TechnicanProcedures", t => t.ProcedureId, cascadeDelete: true)
                .ForeignKey("dbo.Orders", t => t.OrderId, cascadeDelete: true)
                .Index(t => t.ProcedureId)
                .Index(t => t.OrderId);
            
            CreateTable(
                "dbo.TechnicanProcedures",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        Cost = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.AspNetUsers",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Surname = c.String(nullable: false, maxLength: 64),
                        Name = c.String(nullable: false, maxLength: 64),
                        Middlename = c.String(maxLength: 64),
                        Email = c.String(maxLength: 256),
                        EmailConfirmed = c.Boolean(nullable: false),
                        PasswordHash = c.String(),
                        SecurityStamp = c.String(),
                        PhoneNumber = c.String(),
                        PhoneNumberConfirmed = c.Boolean(nullable: false),
                        TwoFactorEnabled = c.Boolean(nullable: false),
                        LockoutEndDateUtc = c.DateTime(),
                        LockoutEnabled = c.Boolean(nullable: false),
                        AccessFailedCount = c.Int(nullable: false),
                        UserName = c.String(nullable: false, maxLength: 256),
                    })
                .PrimaryKey(t => t.Id)
                .Index(t => t.UserName, unique: true, name: "UserNameIndex");
            
            CreateTable(
                "dbo.AspNetUserClaims",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        UserId = c.Int(nullable: false),
                        ClaimType = c.String(),
                        ClaimValue = c.String(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.AspNetUsers", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId);
            
            CreateTable(
                "dbo.AspNetUserLogins",
                c => new
                    {
                        LoginProvider = c.String(nullable: false, maxLength: 128),
                        ProviderKey = c.String(nullable: false, maxLength: 128),
                        UserId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => new { t.LoginProvider, t.ProviderKey, t.UserId })
                .ForeignKey("dbo.AspNetUsers", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId);
            
            CreateTable(
                "dbo.AspNetUserRoles",
                c => new
                    {
                        UserId = c.Int(nullable: false),
                        RoleId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => new { t.UserId, t.RoleId })
                .ForeignKey("dbo.AspNetUsers", t => t.UserId, cascadeDelete: true)
                .ForeignKey("dbo.AspNetRoles", t => t.RoleId, cascadeDelete: true)
                .Index(t => t.UserId)
                .Index(t => t.RoleId);
            
            CreateTable(
                "dbo.Doctors",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Image = c.String(maxLength: 255),
                        Text = c.String(),
                        ApplicationUserId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.AspNetUsers", t => t.ApplicationUserId, cascadeDelete: true)
                .Index(t => t.ApplicationUserId);
            
            CreateTable(
                "dbo.Patients",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        MedicalCardNumber = c.Int(),
                        Age = c.Int(nullable: false),
                        IsMen = c.Boolean(nullable: false),
                        ApplicationUserId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.AspNetUsers", t => t.ApplicationUserId, cascadeDelete: true)
                .Index(t => t.ApplicationUserId);
            
            CreateTable(
                "dbo.PatientVisits",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Date = c.DateTime(),
                        Annotation = c.String(),
                        PatientId = c.Int(nullable: false),
                        DoctorId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Patients", t => t.PatientId, cascadeDelete: true)
                .Index(t => t.PatientId);
            
            CreateTable(
                "dbo.AspNetRoles",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(nullable: false, maxLength: 256),
                    })
                .PrimaryKey(t => t.Id)
                .Index(t => t.Name, unique: true, name: "RoleNameIndex");
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.AspNetUserRoles", "RoleId", "dbo.AspNetRoles");
            DropForeignKey("dbo.PatientVisits", "PatientId", "dbo.Patients");
            DropForeignKey("dbo.Procedures", "PatientVisit_Id", "dbo.PatientVisits");
            DropForeignKey("dbo.Patients", "ApplicationUserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.Orders", "PatientId", "dbo.Patients");
            DropForeignKey("dbo.Doctors", "ApplicationUserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.DentalTechnicans", "ApplicationUserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserRoles", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserLogins", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserClaims", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.Orders", "DentalTechnican_Id", "dbo.DentalTechnicans");
            DropForeignKey("dbo.ToothWorks", "OrderId", "dbo.Orders");
            DropForeignKey("dbo.ToothWorks", "ProcedureId", "dbo.TechnicanProcedures");
            DropForeignKey("dbo.Orders", "ClinicInfoId", "dbo.ClinicInfoes");
            DropForeignKey("dbo.Subcategories", "CategoryId", "dbo.Categories");
            DropForeignKey("dbo.Procedures", "SubcategoryId", "dbo.Subcategories");
            DropIndex("dbo.AspNetRoles", "RoleNameIndex");
            DropIndex("dbo.PatientVisits", new[] { "PatientId" });
            DropIndex("dbo.Patients", new[] { "ApplicationUserId" });
            DropIndex("dbo.Doctors", new[] { "ApplicationUserId" });
            DropIndex("dbo.AspNetUserRoles", new[] { "RoleId" });
            DropIndex("dbo.AspNetUserRoles", new[] { "UserId" });
            DropIndex("dbo.AspNetUserLogins", new[] { "UserId" });
            DropIndex("dbo.AspNetUserClaims", new[] { "UserId" });
            DropIndex("dbo.AspNetUsers", "UserNameIndex");
            DropIndex("dbo.ToothWorks", new[] { "OrderId" });
            DropIndex("dbo.ToothWorks", new[] { "ProcedureId" });
            DropIndex("dbo.Orders", new[] { "DentalTechnican_Id" });
            DropIndex("dbo.Orders", new[] { "ClinicInfoId" });
            DropIndex("dbo.Orders", new[] { "PatientId" });
            DropIndex("dbo.DentalTechnicans", new[] { "ApplicationUserId" });
            DropIndex("dbo.Procedures", new[] { "PatientVisit_Id" });
            DropIndex("dbo.Procedures", new[] { "SubcategoryId" });
            DropIndex("dbo.Subcategories", new[] { "CategoryId" });
            DropTable("dbo.AspNetRoles");
            DropTable("dbo.PatientVisits");
            DropTable("dbo.Patients");
            DropTable("dbo.Doctors");
            DropTable("dbo.AspNetUserRoles");
            DropTable("dbo.AspNetUserLogins");
            DropTable("dbo.AspNetUserClaims");
            DropTable("dbo.AspNetUsers");
            DropTable("dbo.TechnicanProcedures");
            DropTable("dbo.ToothWorks");
            DropTable("dbo.Orders");
            DropTable("dbo.DentalTechnicans");
            DropTable("dbo.ClinicInfoes");
            DropTable("dbo.Procedures");
            DropTable("dbo.Subcategories");
            DropTable("dbo.Categories");
        }
    }
}
