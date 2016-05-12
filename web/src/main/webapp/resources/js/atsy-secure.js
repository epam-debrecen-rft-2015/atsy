function ViewModel(){
    this.aClick = function() {
        $("#secure_logout").submit();
    }
}

ko.applyBindings(new ViewModel());