package com.prj.prjbackend.modules.admin.dto;

public record DashboardTabResponseDTO(
      Integer activeUsers,
      Integer figuresCreated,
      Integer figuresInAlbum
) { }
