function ClickEvent() {
  var sender = event.srcElement || event.target;
  if (sender.nodeName == "LI") {
    var clist = sender.classList;
    if (clist.contains("collapse")) {
      clist.toggle("expand");
    }
  }
}