package com.prj.prjbackend.modules.album;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotNull
    @Lob
    @Column(nullable = false)
    private byte[] coverImage;

    @NotBlank
    @Column(nullable = false)
    private String coverImageType;

    @Positive
    @Column(nullable = false)
    private Integer totalPages;

    @Positive
    @Column(nullable = false)
    private Integer totalStickers;
}
