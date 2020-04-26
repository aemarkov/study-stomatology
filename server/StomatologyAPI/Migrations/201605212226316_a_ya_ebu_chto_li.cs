namespace StomatologyAPI.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class a_ya_ebu_chto_li : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Orders", "TotalCost", c => c.Decimal(precision: 18, scale: 2));
            DropColumn("dbo.Orders", "TotalCose");
        }
        
        public override void Down()
        {
            AddColumn("dbo.Orders", "TotalCose", c => c.Decimal(precision: 18, scale: 2));
            DropColumn("dbo.Orders", "TotalCost");
        }
    }
}
