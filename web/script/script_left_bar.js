var Sliderl=function(object,no,css){return this.init.apply(this,arguments);}
Sliderl.prototype={
	init:function(object,no,css){
		this._isIE=document.all?true:false;
		this._data=[];
		
		this._target=no;
		this._prev_target=-1;
		
		this._select=null;
		this._prevselect=null;
		
		this._title=document.getElementsByTagName("h3");
		this._body=document.getElementsByTagName("ul");
		
		var TempData=null;
		var myThis=this;
		
		for(var i=0;i<this._title.length;++i){
			this._title[i].className=css.title_normal;
			
			this._title[i]._index=i;
			
			this._title[i].onmouseover=function(){
				myThis._target!=this._index?this.className=css.title_select:null;
				}
				
			this._title[i].onmouseout=function(){
				myThis._target!=this._index?this.className=css.title_normal:null;
				}
			
			this._title[i].onclick=function(){
				myThis._prev_target=myThis._target;
				myThis._target=this._index;
				myThis.selectTitle();
				}
			
			var lis=this._body[i].getElementsByTagName("li");
			
			for(var j=0;j<lis.length;++j){
				lis[j].className=css.body_normal;
				lis[j]._parent=this._body[i];
				
				lis[j].onclick=function(){
					myThis._prevselect=myThis._select;
					myThis._select=this;
					
					if(myThis._prevselect!=null)
						myThis._prevselect.className=css.body_normal;
					
					this.className=css.body_select;
				}
			}
			
			TempData={"title":this._title[i],"body":this._body[i],"title_height":this._title[i].offsetHeight,"body_height":this._body[i].offsetHeight,"process":0};
			
			this._body[i].style.display="none";
	
			this._data.push(TempData);
			this._title[i]._data=TempData;
		}

		this._data[this._target].body.style.display="block";
		this._data[this._target].title.className=css.title_select;
		this._data[this._target].process=this._data[this._target].body_height;
		
		this._css=css;
		
		this._process=window.setInterval(function(){return myThis.activeRunning();},24);
	},
	
	selectTitle:function(){
		if(this._target!=this._prev_target){
			this._data[this._prev_target].title.className=this._css.title_normal;
			this._data[this._target].title.className=this._css.title_select;
		}
	},
	
	activeRunning:function(){
		for(var i=0;i<this._data.length;++i){
			if(i==this._target){
				if(this._data[i].process!=0){
					if(this._data[i].process>=this._data[i].body_height){
						this._data[i].process=this._data[i].body_height;
					}
					else{
						var height=parseInt(this._data[i].body.style.height);
						height=height+Math.ceil((this._data[i].body_height-height)/8);
						
						this._data[i].body.style.height=height+"px";
					}
				}
				else{
					this._data[i].body.style.display="block";
					this._data[i].process=this._data[i].body_height/8;
					this._data[i].body.style.height=this._data[i].process+"px";
				}
			}
			else{
				if(this._data[i].process!=0){
					var height=this._data[i].body.offsetHeight-Math.ceil(this._data[i].body.offsetHeight/8);

					if(height<=0){
						this._data[i].body.style.height=0+"px";
						this._data[i].body.style.display="none";
						this._data[i].process=0;
					}
					else{
						this._data[i].body.style.height=height+"px";
						this._data[i].body.style.display="block";
						this._data[i].process=height;
					}	
				}
				else
					this._data[i].body.style.display="none";
			}
		}
	}
}
