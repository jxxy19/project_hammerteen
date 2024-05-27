package org.fullstack4.hammerteen.service;

import org.fullstack4.hammerteen.dto.BbsReplyDTO;

import java.util.List;

public interface BbsReplyServiceIf {
    int regist(BbsReplyDTO bbsReplyDTO);
    List<BbsReplyDTO> replyList (BbsReplyDTO bbsReplyDTO);

    void replyDelete(int replyIdx);
    void replyModify(BbsReplyDTO bbsReplyDTO);

}
