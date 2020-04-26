using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models.BindingModels
{
	/// <summary>
	/// Просто возвращает ID после Put
	/// </summary>
	public class PutResponse
	{
		public int Id { get; set; }

		public PutResponse() { }

		public PutResponse(int id)
		{
			Id = id;
		}
	}
}