/**
 * Created by shifengyuan on 2016/6/14.
 */

// * Strip currency symbols, thousands separators and spaces
function stripCurrency(value, symbol, separator) {
    symbol = (typeof symbol == 'undefined' ? '$' : symbol);
    separator = (typeof separator == 'undefined' ? ',' : separator);

    value = value.replace(symbol, "").replace(separator, "").replace(" ", "");

    return value;
}

// * Format number with currency symbol
function formatCurrency(value, decimals, decpoint, separator, symbol) {
    decimals = (typeof decimals == 'undefined' ? 2 : decimals);
    decpoint = (typeof decpoint == 'undefined' ? '.' : decpoint);
    separator = (typeof separator == 'undefined' ? ',' : separator);
    symbol = (typeof symbol == 'undefined' ? '$' : symbol);

    // .toFixed(): 四舍五入为指定小数位数的数字
    var parts = value.toFixed(decimals).toString().split(decpoint);
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, separator);

    return (symbol + parts.join(decpoint)).toLocaleString();
}
