package com.epam.rft.atsy.web.model.file;


import org.apache.commons.lang3.StringUtils;

import lombok.Data;


@Data
public class CVStatusMonitor {
  private String actualCVPath;
  public static final String CV_STATUS = "cv_status";
  private static CVStatusMonitor CVStatusMonitor = null;

  private CVStatusMonitor() {
    this.restoreActualCVPathToEmpty();
  }

  public static CVStatusMonitor getInstance() {
    if (CVStatusMonitor == null) {
      CVStatusMonitor = new CVStatusMonitor();
    }
    return CVStatusMonitor;
  }

  public void restoreActualCVPathToEmpty() {
    actualCVPath = StringUtils.EMPTY;
  }

  public boolean isActualCVPathEmpty() {
    return actualCVPath.isEmpty();
  }
}
