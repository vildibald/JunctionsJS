var LatEntry3DGJ = function (junctionTemplate) {
    this.junctionTemplate = junctionTemplate;

    // DP1 INCOMINGS
    this.dp1Incoming = null;
    this.dp1IncomingExtender = null;
    this.dp1IncomingExtenderCount = null;

    // DP1 OUTGOINGS
    this.dp1Fork = null;

    this.dp1Outgoing1 = null;
    this.dp1Outgoing1Extender1 = null;
    this.dp1Outgoing1Extender2 = null;
    this.dp1Outgoing1Extender = null;
    this.dp1Outgoing1ExtenderCount = null;

    this.dp1Outgoing2 = null;
    this.dp1Outgoing2Extender1 = null;
    this.dp1Outgoing2Extender2 = null;
    this.dp1Outgoing2Extender = null;
    this.dp1Outgoing2ExtenderCount = null;

    // DP 1 Merge
    this.dp1Merge = null;

    this.dp1MergeIncoming = null;
    this.dp1MergeIncomingExtender1 = null;
    this.dp1MergeIncomingExtender2 = null;
    this.dp1MergeIncomingExtender = null;
    this.dp1MergeIncomingExtenderCount = null;

    this.dp1MergeOutgoing = null;
    this.dp1MergeOutgoingExtender1 = null;
    this.dp1MergeOutgoingExtender2 = null;
    this.dp1MergeOutgoingExtender = null;
    this.dp1MergeOutgoingExtenderCount = null;

    // DP2 FORK
    this.dp2Incoming = null;
    this.dp2Fork = null;

    // DP2 Outgoing1
    this.dp2Outgoing1 = null;
    this.dp2Outgoing1Extender1 = null;
    this.dp2Outgoing1Extender2 = null;
    this.dp2Outgoing1Extender = null;
    this.dp2Outgoing1ExtenderCount = null;

    // DP2 Outgoing2
    this.dp2Outgoing2 = null;
    this.dp2Outgoing2Extender1 = null;
    this.dp2Outgoing2Extender2 = null;
    this.dp2Outgoing2ExtenderCount = null;
    this.dp2Outgoing2Extender = null;


}

function parseEntries(data) {
    var lines = data.split('\n');
    var entries = [];
    for (var i = 1; i < lines.length - 1; i++) {
        var line = lines[i].split(';');

        // displayContents(entries);
        //remove possible "",
        //i.e. if line[j] == ""..."" then line = "..."
        for(var j = 0;j<line.length;j++){
            if(line[j].charAt(0)=='\"'){
                line[j]=line[j].substr(1,line[j].length-2);
            }

        }

        var entry = new LatEntry3DGJ();
        entry.junctionTemplate = line[6];
        entry.dp1Incoming = line[17];
        entry.dp1IncomingExtender = line[18];
        entry.dp1IncomingExtenderCount = line[19];
        entry.dp1Fork = line[20];
        entry.dp1Outgoing1 = line[21];
        entry.dp1Outgoing1Extender1 = line[22];
        entry.dp1Outgoing1Extender2 = line[23];
        entry.dp1Outgoing1Extender = line[24];
        entry.dp1Outgoing1ExtenderCount = line[25];
        entry.dp1Outgoing2 = line[26];
        entry.dp1Outgoing2Extender1 = line[27];
        entry.dp1Outgoing2Extender2 = line[28];
        entry.dp1Outgoing2Extender = line[29];
        entry.dp1Outgoing2ExtenderCount = line[30];
        entry.dp1Merge = line[31];
        entry.dp1MergeIncoming = line[32];
        entry.dp1MergeIncomingExtender1 = line[33];
        entry.dp1MergeIncomingExtender2 = line[34];
        entry.dp1MergeIncomingExtender = line[35];
        entry.dp1MergeIncomingExtenderCount = line[36];
        entry.dp1MergeOutgoing = line[37];
        entry.dp1MergeOutgoingExtender1 = line[38];
        entry.dp1MergeOutgoingExtender2 = line[39];
        entry.dp1MergeOutgoingExtender = line[40];
        entry.dp1MergeOutgoingExtenderCount = line[41];
        entry.dp2Incoming = line[42];
        entry.dp2Fork = line[43];
        entry.dp2Outgoing1 = line[44];
        entry.dp2Outgoing1Extender1 = line[45];
        entry.dp2Outgoing1Extender2 = line[46];
        entry.dp2Outgoing1Extender = line[47];
        entry.dp2Outgoing1ExtenderCount = line[48];
        entry.dp2Outgoing2 = line[49];
        entry.dp2Outgoing2Extender1 = line[50];
        entry.dp2Outgoing2Extender2 = line[51];
        entry.dp2Outgoing2Extender = line[52];
        entry.dp2Outgoing2ExtenderCount = line[53];
        entries.push(entry);

    }
    return entries;
}