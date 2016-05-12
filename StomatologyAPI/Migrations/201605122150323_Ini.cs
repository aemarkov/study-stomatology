namespace StomatologyAPI.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Ini : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.PatientVisits", "Doctor_Id", "dbo.AspNetUsers");
            DropForeignKey("dbo.Orders", "Patient_Id", "dbo.AspNetUsers");
            DropForeignKey("dbo.PatientVisits", "Patient_Id", "dbo.AspNetUsers");
            DropForeignKey("dbo.Orders", "Technican_Id", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserClaims", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserLogins", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserRoles", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserRoles", "RoleId", "dbo.AspNetRoles");
            DropIndex("dbo.Orders", new[] { "Technican_Id" });
            DropIndex("dbo.Orders", new[] { "Patient_Id" });
            DropIndex("dbo.AspNetUserClaims", new[] { "UserId" });
            DropIndex("dbo.AspNetUserLogins", new[] { "UserId" });
            DropIndex("dbo.AspNetUserRoles", new[] { "UserId" });
            DropIndex("dbo.AspNetUserRoles", new[] { "RoleId" });
            DropIndex("dbo.PatientVisits", new[] { "Doctor_Id" });
            DropIndex("dbo.PatientVisits", new[] { "Patient_Id" });
            DropColumn("dbo.Orders", "DentalTechnicanID");
            DropColumn("dbo.Orders", "PatientID");
            DropColumn("dbo.PatientVisits", "PatientID");
            RenameColumn(table: "dbo.Orders", name: "Technican_Id", newName: "DentalTechnicanID");
            RenameColumn(table: "dbo.Orders", name: "Patient_Id", newName: "PatientID");
            RenameColumn(table: "dbo.PatientVisits", name: "Patient_Id", newName: "PatientID");
            DropPrimaryKey("dbo.AspNetUsers");
            DropPrimaryKey("dbo.AspNetUserLogins");
            DropPrimaryKey("dbo.AspNetUserRoles");
            DropPrimaryKey("dbo.AspNetRoles");
            AlterColumn("dbo.Orders", "DentalTechnicanID", c => c.Int());
            AlterColumn("dbo.Orders", "DentalTechnicanID", c => c.Int());
            AlterColumn("dbo.Orders", "PatientID", c => c.Int(nullable: false));
            AlterColumn("dbo.AspNetUsers", "Id", c => c.Int(nullable: false, identity: true));
            AlterColumn("dbo.AspNetUserClaims", "UserId", c => c.Int(nullable: false));
            AlterColumn("dbo.AspNetUserLogins", "UserId", c => c.Int(nullable: false));
            AlterColumn("dbo.AspNetUserRoles", "UserId", c => c.Int(nullable: false));
            AlterColumn("dbo.AspNetUserRoles", "RoleId", c => c.Int(nullable: false));
            AlterColumn("dbo.PatientVisits", "DoctorID", c => c.Int());
            AlterColumn("dbo.PatientVisits", "PatientID", c => c.Int(nullable: false));
            AlterColumn("dbo.AspNetRoles", "Id", c => c.Int(nullable: false, identity: true));
            AddPrimaryKey("dbo.AspNetUsers", "Id");
            AddPrimaryKey("dbo.AspNetUserLogins", new[] { "LoginProvider", "ProviderKey", "UserId" });
            AddPrimaryKey("dbo.AspNetUserRoles", new[] { "UserId", "RoleId" });
            AddPrimaryKey("dbo.AspNetRoles", "Id");
            CreateIndex("dbo.Orders", "PatientID");
            CreateIndex("dbo.Orders", "DentalTechnicanID");
            CreateIndex("dbo.PatientVisits", "PatientID");
            CreateIndex("dbo.AspNetUserRoles", "UserId");
            CreateIndex("dbo.AspNetUserRoles", "RoleId");
            CreateIndex("dbo.AspNetUserClaims", "UserId");
            CreateIndex("dbo.AspNetUserLogins", "UserId");
            AddForeignKey("dbo.Orders", "PatientID", "dbo.AspNetUsers", "Id", cascadeDelete: true);
            AddForeignKey("dbo.PatientVisits", "PatientID", "dbo.AspNetUsers", "Id", cascadeDelete: true);
            AddForeignKey("dbo.AspNetUserClaims", "UserId", "dbo.AspNetUsers", "Id", cascadeDelete: true);
            AddForeignKey("dbo.AspNetUserLogins", "UserId", "dbo.AspNetUsers", "Id", cascadeDelete: true);
            AddForeignKey("dbo.AspNetUserRoles", "UserId", "dbo.AspNetUsers", "Id", cascadeDelete: true);
            AddForeignKey("dbo.Orders", "DentalTechnicanID", "dbo.AspNetUsers", "Id");
            AddForeignKey("dbo.AspNetUserRoles", "RoleId", "dbo.AspNetRoles", "Id", cascadeDelete: true);
            DropColumn("dbo.PatientVisits", "Doctor_Id");
        }
        
        public override void Down()
        {
            AddColumn("dbo.PatientVisits", "Doctor_Id", c => c.String(maxLength: 128));
            DropForeignKey("dbo.AspNetUserRoles", "RoleId", "dbo.AspNetRoles");
            DropForeignKey("dbo.Orders", "DentalTechnicanID", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserRoles", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserLogins", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserClaims", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.PatientVisits", "PatientID", "dbo.AspNetUsers");
            DropForeignKey("dbo.Orders", "PatientID", "dbo.AspNetUsers");
            DropIndex("dbo.AspNetUserLogins", new[] { "UserId" });
            DropIndex("dbo.AspNetUserClaims", new[] { "UserId" });
            DropIndex("dbo.AspNetUserRoles", new[] { "RoleId" });
            DropIndex("dbo.AspNetUserRoles", new[] { "UserId" });
            DropIndex("dbo.PatientVisits", new[] { "PatientID" });
            DropIndex("dbo.Orders", new[] { "DentalTechnicanID" });
            DropIndex("dbo.Orders", new[] { "PatientID" });
            DropPrimaryKey("dbo.AspNetRoles");
            DropPrimaryKey("dbo.AspNetUserRoles");
            DropPrimaryKey("dbo.AspNetUserLogins");
            DropPrimaryKey("dbo.AspNetUsers");
            AlterColumn("dbo.AspNetRoles", "Id", c => c.String(nullable: false, maxLength: 128));
            AlterColumn("dbo.PatientVisits", "PatientID", c => c.String(maxLength: 128));
            AlterColumn("dbo.PatientVisits", "DoctorID", c => c.Int(nullable: false));
            AlterColumn("dbo.AspNetUserRoles", "RoleId", c => c.String(nullable: false, maxLength: 128));
            AlterColumn("dbo.AspNetUserRoles", "UserId", c => c.String(nullable: false, maxLength: 128));
            AlterColumn("dbo.AspNetUserLogins", "UserId", c => c.String(nullable: false, maxLength: 128));
            AlterColumn("dbo.AspNetUserClaims", "UserId", c => c.String(nullable: false, maxLength: 128));
            AlterColumn("dbo.AspNetUsers", "Id", c => c.String(nullable: false, maxLength: 128));
            AlterColumn("dbo.Orders", "PatientID", c => c.String(maxLength: 128));
            AlterColumn("dbo.Orders", "DentalTechnicanID", c => c.String(maxLength: 128));
            AlterColumn("dbo.Orders", "DentalTechnicanID", c => c.Int(nullable: false));
            AddPrimaryKey("dbo.AspNetRoles", "Id");
            AddPrimaryKey("dbo.AspNetUserRoles", new[] { "UserId", "RoleId" });
            AddPrimaryKey("dbo.AspNetUserLogins", new[] { "LoginProvider", "ProviderKey", "UserId" });
            AddPrimaryKey("dbo.AspNetUsers", "Id");
            RenameColumn(table: "dbo.PatientVisits", name: "PatientID", newName: "Patient_Id");
            RenameColumn(table: "dbo.Orders", name: "PatientID", newName: "Patient_Id");
            RenameColumn(table: "dbo.Orders", name: "DentalTechnicanID", newName: "Technican_Id");
            AddColumn("dbo.PatientVisits", "PatientID", c => c.Int(nullable: false));
            AddColumn("dbo.Orders", "PatientID", c => c.Int(nullable: false));
            AddColumn("dbo.Orders", "DentalTechnicanID", c => c.Int(nullable: false));
            CreateIndex("dbo.PatientVisits", "Patient_Id");
            CreateIndex("dbo.PatientVisits", "Doctor_Id");
            CreateIndex("dbo.AspNetUserRoles", "RoleId");
            CreateIndex("dbo.AspNetUserRoles", "UserId");
            CreateIndex("dbo.AspNetUserLogins", "UserId");
            CreateIndex("dbo.AspNetUserClaims", "UserId");
            CreateIndex("dbo.Orders", "Patient_Id");
            CreateIndex("dbo.Orders", "Technican_Id");
            AddForeignKey("dbo.AspNetUserRoles", "RoleId", "dbo.AspNetRoles", "Id", cascadeDelete: true);
            AddForeignKey("dbo.AspNetUserRoles", "UserId", "dbo.AspNetUsers", "Id", cascadeDelete: true);
            AddForeignKey("dbo.AspNetUserLogins", "UserId", "dbo.AspNetUsers", "Id", cascadeDelete: true);
            AddForeignKey("dbo.AspNetUserClaims", "UserId", "dbo.AspNetUsers", "Id", cascadeDelete: true);
            AddForeignKey("dbo.Orders", "Technican_Id", "dbo.AspNetUsers", "Id");
            AddForeignKey("dbo.PatientVisits", "Patient_Id", "dbo.AspNetUsers", "Id");
            AddForeignKey("dbo.Orders", "Patient_Id", "dbo.AspNetUsers", "Id");
            AddForeignKey("dbo.PatientVisits", "Doctor_Id", "dbo.AspNetUsers", "Id");
        }
    }
}
