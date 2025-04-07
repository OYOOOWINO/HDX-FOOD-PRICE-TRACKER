import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { useSidebar } from "@/components/ui/sidebar"

export function Trigger() {
    const { toggleSidebar } = useSidebar()

    return <Avatar onClick={toggleSidebar}>
        <AvatarImage src="simba.webp" />
        <AvatarFallback>TS</AvatarFallback>
    </Avatar>

}
