function getSchemaDisplay(schema) {
    var display = [];

    angular.forEach(schema, function(value, key) {
        if (!isPromiseOrResolved(key)) {
            var obj = {};

            obj['name'] = key;
            obj['displayOrder'] = value.displayOrder;
            obj['displayName'] = value.displayName;

            display.push(obj);
        }
    });

    return display;
}

function isPromiseOrResolved(key) {
    if (key == '$promise' || key == '$resolved') {
        return true;
    }

    return false;
}

function bindCharsRemaining(characterLimit, messageElement, sourceText) {
    $(messageElement).html(characterLimit + " characters remaining.");

    $(sourceText).bind('keyup', function() {
        var charactersUsed = $(this).val().length;

        if (charactersUsed > characterLimit) {
            charactersUsed = characterLimit;
            $(this).val($(this).val().substr(0, characterLimit));
            $(this).scrollTop($(this)[0].scrollHeight);
        }

        var charactersRemaining = characterLimit - charactersUsed;

        $(messageElement).html(charactersRemaining + " characters remaining.");
    });
}