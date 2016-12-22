var source;

function dragStarted(evt) {
	//start drag
	source=evt.target;
	//set data
	evt.dataTransfer.setData("text/plain", evt.target.innerHTML);
	//specify allowed transfer
	evt.dataTransfer.effectAllowed = "move";
};

function draggingOver(evt){
	//drag over
	evt.preventDefault();
	//specify operation
	evt.dataTransfer.dropEffect = "move";
};

function dropped(evt){
	//drop
	evt.preventDefault();
	evt.stopPropagation();
	//update text in dragged item
	source.innerHTML = evt.target.innerHTML;
	//update text in drop target
	evt.target.innerHTML = evt.dataTransfer.getData("text/plain");
};