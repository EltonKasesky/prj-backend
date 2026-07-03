package com.prj.prjbackend.modules.usersticker;

import com.prj.prjbackend.modules.sticker.Sticker;
import com.prj.prjbackend.modules.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(
        name = "user_stickers",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "sticker_id"}
        )
)
public class UserSticker {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sticker_id", nullable = false)
    private Sticker sticker;

    @Min(1)
    @Column(nullable = false)
    private Integer quantity;
}
