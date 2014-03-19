define(["jquery","backbone","underscore","pages/spark/views/template/student-page.html"],function($,Backbone,_,template){var page=Backbone.View.extend({name:"page",subregions:{header:"pages/spark/views/template/header",sidebar:"pages/spark/views/template/sidebar"},initialize:function(){this.bind("view:appended",this.scroll),this.bind("view:updated",this.scroll)},scroll:function(){$("html, body").scrollTop(0),$(window).trigger("scroll")},render:function(){var regions=this.region.regions,self=this,page=$(template());return self.$el.append(page),_.each(["header","sidebar","body"],function(type){if(regions[type])page.find(".coursera-"+type).replaceWith(regions[type].view.el);else if("body"!=type)page.find(".coursera-"+type).hide();else page.find(".coursera-body").replaceWith($("#spark").show())}),self}});return page});