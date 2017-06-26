package com.sfy.dao;

import com.sfy.domain.Code;
import java.util.List;


public interface CodeMapDao {

    List<Code> queryCode(Code code);

}
