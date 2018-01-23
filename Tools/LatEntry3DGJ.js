/**
 * Created by SKKSC-Visitors on 17. 6. 2015.
 */
var LatEntry3DGJ = function(junctionTemplate){
    this.junctionTemplate = junctionTemplate;

    // DP1 INCOMINGS
    this.dp1Incoming = null;
    this.dp1IncomingExtender = null;
    this.dp1IncomingExtenderCount = null;

    // DP1 OUTGOINGS
    this.dp1Fork = null;

    this.dp1Outgoing1;
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
    this.dp2Outgoing2;
    this.dp2Outgoing2Extender1 = null;
    this.dp2Outgoing2Extender2 = null;
    this.dp2Outgoing2ExtenderCount = null;
    this.dp2Outgoing2Extender = null;

}