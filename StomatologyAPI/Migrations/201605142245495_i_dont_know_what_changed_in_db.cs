namespace StomatologyAPI.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class i_dont_know_what_changed_in_db : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Orders", "DoctorId", c => c.Int());
            CreateIndex("dbo.Orders", "DoctorId");
            AddForeignKey("dbo.Orders", "DoctorId", "dbo.Doctors", "Id");
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Orders", "DoctorId", "dbo.Doctors");
            DropIndex("dbo.Orders", new[] { "DoctorId" });
            DropColumn("dbo.Orders", "DoctorId");
        }
    }
}
