export function readFile(file){
	return new Promise(resolve =>{
		let reader = new FileReader();
		reader.readAsArrayBuffer(file);
		reader.onload = ev =>{
			resolve(ev.target.result);
		};
	});
}
