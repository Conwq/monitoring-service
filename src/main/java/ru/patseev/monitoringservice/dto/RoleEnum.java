package ru.patseev.monitoringservice.dto;

/**
 * Available roles for users.
 */
public enum RoleEnum {
	ADMIN() {
		@Override
		public int getRoleId() {
			return 1;
		}
	},

	USER {
		@Override
		public int getRoleId() {
			return 2;
		}
	};

	/**
	 * Get the role ID associated with the enum constant.
	 *
	 * @return The role ID.
	 */
	public abstract int getRoleId();
}
