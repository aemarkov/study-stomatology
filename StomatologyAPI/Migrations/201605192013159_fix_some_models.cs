namespace StomatologyAPI.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class fix_some_models : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Orders", "IsClosed", c => c.Boolean(nullable: false));
            AddColumn("dbo.Patients", "DateOfBirth", c => c.DateTime(nullable: false));
            AddColumn("dbo.PatientVisits", "IsClosed", c => c.Boolean(nullable: false));
            DropColumn("dbo.Patients", "Age");
        }
        
        public override void Down()
        {
            AddColumn("dbo.Patients", "Age", c => c.Int(nullable: false));
            DropColumn("dbo.PatientVisits", "IsClosed");
            DropColumn("dbo.Patients", "DateOfBirth");
            DropColumn("dbo.Orders", "IsClosed");
        }
    }
}
